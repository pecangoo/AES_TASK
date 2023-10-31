package org.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class MetaDatas {

    public static Long getMetaSize(String pathStr) {
        Long metaSize = 0L;
        try {

            // Путь к файлу
            Path path = Paths.get(pathStr);

            // Создание пользовательских атрибутов
            UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

            // Чтение значения размера из метаданных
            String attributeSize = "file.size";

            int size = view.size(attributeSize);
            ByteBuffer buffer = ByteBuffer.allocateDirect(size);
            view.read(attributeSize, buffer);
            buffer.flip();

            String valueSize = Charset.defaultCharset().decode(buffer).toString();
            metaSize = Long.valueOf(valueSize);
            //     System.out.println("Размер файла: " + valueSize);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaSize;
    }


    public static void putMetaSize(String pathStr, Long size) {
        try {

            Path path = Paths.get(pathStr);

            // Создание пользовательских атрибутов
            UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

            // Запись значения размера файла в метаданные
            String attributeSize = "file.size";
            String valueSize = size.toString(); // Размер файла

            try {
                view.write(attributeSize, Charset.defaultCharset().encode(valueSize));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
