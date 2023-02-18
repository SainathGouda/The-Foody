package com.example.thefoody.Listeners;

import com.example.thefoody.Models.Root;

import java.util.List;

public interface JokesListener {
    void didFetch(Root response, String message);
    void didError(String message);
}
