package com.damian.Blog2.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.damian.Blog2.Models.CommentCSV;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParserComment {


    public static List<CommentCSV> parse() throws IOException {

        String fileName = "src/main/resources/Comments.csv";
        Path myPath = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            ColumnPositionMappingStrategy<CommentCSV> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(CommentCSV.class);
            String[] fields = {"id", "username", "idPost", "commentContent"};
            strategy.setColumnMapping(fields);

            CsvToBean<CommentCSV> csvToBean = new CsvToBeanBuilder<CommentCSV>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CommentCSV> comments = csvToBean.parse();
            comments.remove(0);

            return comments;

        }

    }
}
