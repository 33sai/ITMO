cd task2
javac -d out src\model\Event.java src\model\Schedule.java src\binary\BinaryReader.java src\binary\BinaryWriter.java src\lab4hcl\MainHcl.java src\lab4hcl\serializer\HclSerializer.java
java -cp out lab4hcl.MainHcl