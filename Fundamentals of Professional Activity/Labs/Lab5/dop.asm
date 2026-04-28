ORG 0x020

; I/O constants 
READY_MASK: WORD 0x40
POS1:       WORD 0x10
POS2:       WORD 0x20
MINUS_P3:   WORD 0x3A
BLANK_P3:   WORD 0x3F

; state 
PHASE:      WORD 0
OP:         WORD 0
KEY:        WORD 0
DIG:        WORD 0
BINERR:     WORD 0

OP1_10:     WORD 0	
OP2_10:     WORD 0
OP1_2:      WORD 0
OP2_2:      WORD 0

RES:        WORD 0
ABS:        WORD 0
SIGN:       WORD 0
HUND:       WORD 0
TENS:       WORD 0
ONES:       WORD 0
TMP:        WORD 0
OUTVAL:     WORD 0

START:
        CLA
        ST $PHASE
        ST $OP
        ST $KEY
        ST $DIG
        ST $BINERR
        ST $OP1_10
        ST $OP2_10
        ST $OP1_2
        ST $OP2_2
        ST $RES
        ST $ABS
        ST $SIGN
        ST $HUND
        ST $TENS
        ST $ONES
        ST $TMP
        ST $OUTVAL

READ_KEY:
WAIT_RDY:
        IN 0x1D
        AND $READY_MASK
        BEQ WAIT_RDY

        IN 0x1C
        ST $KEY

        LD $KEY
        CMP #10
        BLT HANDLE_DIGIT

        LD $KEY
        CMP #0x0B
        BEQ OP_PLUS

        LD $KEY
        CMP #0x0A
        BEQ OP_MINUS

        LD $KEY
        CMP #0x0D
        BEQ OP_AND

        LD $KEY
        CMP #0x0C
        BEQ OP_OR

        LD $KEY
        CMP #0x0F
        BEQ ON_EQUAL

        JUMP $READ_KEY

OP_PLUS:
        LD $PHASE
        BEQ OP_PLUS_OK
        JUMP $READ_KEY
OP_PLUS_OK:
        LD #0x0B
        ST $OP
        LD #1
        ST $PHASE
        JUMP $READ_KEY

OP_MINUS:
        LD $PHASE
        BEQ OP_MINUS_OK
        JUMP $READ_KEY
OP_MINUS_OK:
        LD #0x0A
        ST $OP
        LD #1
        ST $PHASE
        JUMP $READ_KEY

OP_AND:
        LD $PHASE
        BEQ OP_AND_OK
        JUMP $READ_KEY
OP_AND_OK:
        LD #0x0D
        ST $OP
        LD #1
        ST $PHASE
        JUMP $READ_KEY

OP_OR:
        LD $PHASE
        BEQ OP_OR_OK
        JUMP $READ_KEY
OP_OR_OK:
        LD #0x0C
        ST $OP
        LD #1
        ST $PHASE
        JUMP $READ_KEY

ON_EQUAL:
        LD $PHASE
        CMP #1
        BEQ ON_EQUAL_OK
        JUMP $READ_KEY
ON_EQUAL_OK:
        JUMP $COMPUTE

HANDLE_DIGIT:
        LD $KEY
        ST $DIG

        LD $PHASE
        BEQ DIGIT_OP1
        CMP #1
        BEQ DIGIT_OP2
        JUMP $READ_KEY

DIGIT_OP1:
        LD $OP1_10
        ASL
        ST $TMP
        ASL
        ASL
        ADD $TMP
        ADD $DIG
        ST $OP1_10

        LD $OP1_2
        ASL
        ADD $DIG
        ST $OP1_2

        LD $DIG
        CMP #2
        BLT DIG1_OK
        LD #1
        ST $BINERR
DIG1_OK:
        JUMP $READ_KEY

DIGIT_OP2:
        LD $OP2_10
        ASL
        ST $TMP
        ASL
        ASL
        ADD $TMP
        ADD $DIG
        ST $OP2_10

        LD $OP2_2
        ASL
        ADD $DIG
        ST $OP2_2

        LD $DIG
        CMP #2
        BLT DIG2_OK
        LD #1
        ST $BINERR
DIG2_OK:
        JUMP $READ_KEY

COMPUTE:
        LD $OP
        CMP #0x0B
        BEQ DO_ADD

        LD $OP
        CMP #0x0A
        BEQ DO_SUB

        LD $OP
        CMP #0x0D
        BEQ DO_AND

        LD $OP
        CMP #0x0C
        BEQ DO_OR

        JUMP $RESET

DO_ADD:
        LD $OP1_10
        ADD $OP2_10
        ST $RES
        JUMP $SHOW_RES

DO_SUB:
        LD $OP1_10
        SUB $OP2_10
        ST $RES
        JUMP $SHOW_RES

DO_AND:
        LD $BINERR
        BEQ DO_AND_OK2
        CLA
        ST $RES
        JUMP $SHOW_RES
DO_AND_OK2:
        LD $OP1_2
        AND $OP2_2
        ST $RES
        JUMP $SHOW_RES

DO_OR:
        LD $BINERR
        BEQ DO_OR_OK2
        CLA
        ST $RES
        JUMP $SHOW_RES
DO_OR_OK2:
        LD $OP1_2
        OR $OP2_2
        ST $RES

SHOW_RES:
        LD $RES
        BPL RES_NONNEG
        LD #1
        ST $SIGN
        LD $RES
        NEG
        ST $ABS
        JUMP $SPLIT100

RES_NONNEG:
        CLA
        ST $SIGN
        LD $RES
        ST $ABS

SPLIT100:
        CLA
        ST $HUND
HUND_LOOP:
        LD $ABS
        CMP #100
        BLT SPLIT10
        SUB #100
        ST $ABS
        LD $HUND
        INC
        ST $HUND
        BR HUND_LOOP

SPLIT10:
        CLA
        ST $TENS
TENS_LOOP:
        LD $ABS
        CMP #10
        BLT STORE_ONES
        SUB #10
        ST $ABS
        LD $TENS
        INC
        ST $TENS
        BR TENS_LOOP

STORE_ONES:
        LD $ABS
        ST $ONES

        LD $ONES
        CALL $OUT7

        LD $TENS
        OR $POS1
        CALL $OUT7

        LD $HUND
        OR $POS2
        CALL $OUT7

        LD $SIGN
        BEQ CLEAR_SIGN
        LD $MINUS_P3
        CALL $OUT7
        JUMP $RESET

CLEAR_SIGN:
        LD $BLANK_P3
        CALL $OUT7

RESET:
        CLA
        ST $PHASE
        ST $OP
        ST $KEY
        ST $DIG
        ST $BINERR
        ST $OP1_10
        ST $OP2_10
        ST $OP1_2
        ST $OP2_2
        ST $RES
        ST $ABS
        ST $SIGN
        ST $HUND
        ST $TENS
        ST $ONES
        ST $TMP
        ST $OUTVAL
        JUMP $READ_KEY

OUT7:
        ST $OUTVAL
OUT7_WAIT:
        IN 0x15
        AND $READY_MASK
        BEQ OUT7_WAIT
        LD $OUTVAL
        OUT 0x14
        RET

END
