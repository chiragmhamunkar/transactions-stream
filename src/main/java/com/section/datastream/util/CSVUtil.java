package com.section.datastream.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVUtil {

    public static <T> List<T> read(Path path, Class<T> clazz) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
        MappingIterator<T> transactionsMappingItr =
                csvMapper.readerFor(clazz)
                .with(csvSchema)
                .readValues(path.toFile());
        List<T> rows = transactionsMappingItr.readAll();
        return rows;
    }

}
