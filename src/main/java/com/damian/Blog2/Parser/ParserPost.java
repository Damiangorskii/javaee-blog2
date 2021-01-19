package com.damian.Blog2.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.damian.Blog2.Models.PostCSV;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class ParserPost {

    public static List<String> allTags() throws IOException {

        String fileName = "src/main/resources/ManyPostsManyAuthors.csv";
        Path myPath = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            ColumnPositionMappingStrategy<PostCSV> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(PostCSV.class);
            String[] fields = {"id", "authors", "postContent", "tags"};
            strategy.setColumnMapping(fields);

            CsvToBean<PostCSV> csvToBean = new CsvToBeanBuilder<PostCSV>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<PostCSV> temp = csvToBean.parse();

            List<String> tags = new LinkedList<>();

            List<PostCSV> posts = new LinkedList<>();

            for (PostCSV p : temp){
                PostCSV newPosts = new PostCSV(p.getId(), authorsToString(p.getAuthors()), p.getPostContent(), p.getTags());

                posts.add(newPosts);

            }

            for (int i=1; i<posts.size(); i++){
                String[] data = posts.get(i).getTags().split(" ");
                for (int j=0; j<data.length; j++){
                    if (!tags.contains(data[j])){
                        tags.add(data[j]);
                    }
                }
            }

            return tags;

        }
    }


    public static List<String> allAuthors() throws IOException {

        String fileName = "src/main/resources/ManyPostsManyAuthors.csv";
        Path myPath = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            ColumnPositionMappingStrategy<PostCSV> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(PostCSV.class);
            String[] fields = {"id", "authors", "postContent", "tags"};
            strategy.setColumnMapping(fields);

            CsvToBean<PostCSV> csvToBean = new CsvToBeanBuilder<PostCSV>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<PostCSV> temp = csvToBean.parse();

            List<String> authors = new LinkedList<>();

            List<PostCSV> posts = new LinkedList<>();

            for (PostCSV p : temp){
                PostCSV newPosts = new PostCSV(p.getId(), authorsToString(p.getAuthors()), p.getPostContent(), p.getTags());

                posts.add(newPosts);

            }

            for (int i=1; i<posts.size(); i++){
                String[] data = posts.get(i).getAuthors().split(", ");
                for (int j=0; j<data.length; j++){
                    if (!authors.contains(data[j])){
                        authors.add(data[j]);
                    }
                }
            }

            return authors;

        }
    }

    public static List<PostCSV> parse() throws IOException {

        String fileName = "src/main/resources/ManyPostsManyAuthors.csv";
        Path myPath = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            ColumnPositionMappingStrategy<PostCSV> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(PostCSV.class);
            String[] fields = {"id", "authors", "postContent", "tags"};
            strategy.setColumnMapping(fields);

            CsvToBean<PostCSV> csvToBean = new CsvToBeanBuilder<PostCSV>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<PostCSV> temp = csvToBean.parse();

            List<PostCSV> posts = new LinkedList<>();

            for (int i=1; i<temp.size(); i++){
                PostCSV newPosts = new PostCSV(temp.get(i).getId(), authorsToString(temp.get(i).getAuthors()), temp.get(i).getPostContent(), temp.get(i).getTags());

                posts.add(newPosts);

            }
            return posts;

        }
    }


    public static String authorsToString(String a){


        String text = a;

        text = text.replaceAll("\"authors\"", "");


        int N = StringUtils.countMatches(text, ":");
        String authors = "";

        for(int j = 0; j < N; j++) {
            authors = authors + text.substring(text.indexOf(':') + 2, text.indexOf('}') - 1);
            if (j<N-1){
                authors = authors + ", ";
            }
            text = text.substring(text.indexOf('}') + 1);
        }

        return authors;
    }
}