package com.roopy.netflix.repository;

import com.roopy.entity.FavoriteMovie;
import com.roopy.entity.FavoriteMovieIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyMovieRepository extends JpaRepository<FavoriteMovie, FavoriteMovieIdentity> {

    @Query("SELECT f FROM FavoriteMovie f WHERE f.favoriteMovieIdentity.userId = ?1")
    List<FavoriteMovie> findFavoriteMovies(Long userId);
}