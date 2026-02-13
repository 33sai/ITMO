package data;

public class MusicBand {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer singlesCount; //Поле может быть null, Значение поля должно быть больше 0
    private java.time.LocalDate establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле может быть null
    private Studio studio; //Поле может быть null
}
