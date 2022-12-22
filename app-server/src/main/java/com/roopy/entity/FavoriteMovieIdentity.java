package com.roopy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class FavoriteMovieIdentity implements Serializable {

    @Serial
    private static final long serialVersionUID = 7504916484862660724L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "movie_id", length = 6)
    private Long movieId;


}
