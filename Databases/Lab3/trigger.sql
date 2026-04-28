
-- Function: check_person_hypnotic_state

CREATE OR REPLACE FUNCTION check_person_hypnotic_state(p_person_id BIGINT)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    v_is_hypnotic BOOLEAN;
BEGIN
    -- Check if the person has an active hypnotic state
    -- the most recent record by id with state_type = 'hypnotic'
    SELECT (state_type = 'hypnotic') INTO v_is_hypnotic
    FROM State
    WHERE person_id = p_person_id
    ORDER BY id DESC
    LIMIT 1;

    -- If no records exist, the person is not hypnotized
    IF v_is_hypnotic IS NULL THEN
        RETURN FALSE;
    END IF;

    RETURN v_is_hypnotic;
END;
$$;



-- Trigger Function: fn_auto_set_state_on_action
CREATE OR REPLACE FUNCTION fn_auto_set_state_on_action()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_person_name VARCHAR(100);
    v_is_hypnotic BOOLEAN;
BEGIN
    -- Retrieve the person's name for informative error messages
    SELECT name INTO v_person_name
    FROM Person
    WHERE id = NEW.person_id;

    -- Check whether the person is in a hypnotic state
    v_is_hypnotic := check_person_hypnotic_state(NEW.person_id);

    IF v_is_hypnotic THEN
        -- Person is hypnotized: prevent inserting a new action
        RAISE EXCEPTION
            'Cannot add action: person % (id=%) is in a hypnotic state.',
            v_person_name, NEW.person_id;
    END IF;

    -- Automatically insert a new state 'normal'
    -- (the person starts performing an action)
    INSERT INTO State (state_type, person_id)
    VALUES ('normal', NEW.person_id);

    RAISE NOTICE
        'Person % (id=%) started an action. State set to: normal.',
        v_person_name, NEW.person_id;
    RETURN NEW;
END;
$$;


-- Trigger: auto_set_person_state_on_action
CREATE TRIGGER auto_set_person_state_on_action
BEFORE INSERT
ON PersonAction
FOR EACH ROW
EXECUTE FUNCTION fn_auto_set_state_on_action();