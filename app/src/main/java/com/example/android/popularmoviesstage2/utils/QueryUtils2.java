package com.example.android.popularmoviesstage2.utils;

import android.os.Environment;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emkayx on 06-01-2018.
 */

public class QueryUtils2 {

public static List<Movie> fetchFavoriteMovies() {
    ArrayList<Movie> favoriteMovieList = new ArrayList<Movie>();

    BufferedReader br = null;


    try {
        br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getPath() + "/storage.json"));
        if (br.readLine() != null) {
            favoriteMovieList = (new Gson()).fromJson(br,
                    new TypeToken<ArrayList<Movie>>() {
                    }.getType());


        }
        else{
            favoriteMovieList=null;
        }
    } catch (FileNotFoundException e1) {
        e1.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
     return favoriteMovieList;
}


/*
    list2 = (new Gson()).fromJson(stringJson,
            new TypeToken<ArrayList<Checklist>>() {
    }.getType());

    System.out.println("####### print out put ######");

    for (int i = 0; i < list2.size(); i++) {
        Checklist checklist = list2.get(i);
        System.out.println(checklist.getType_check() + "");
        System.out.println(checklist.getDescription() + "");

        ArrayList<Category> categorys = checklist.getCategories();

        for (int j = 0; j < categorys.size(); j++) {
            Category category = categorys.get(j);
            System.out.println(category.getCategory_id() + "");
            System.out.println(category.getDescription() + "");
        }

    }*/
}
