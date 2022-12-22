package com.roopy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FavoriteMovie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1187874270828549690L;

    @EmbeddedId
    private FavoriteMovieIdentity favoriteMovieIdentity;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "img", length = 200)
    private String img;


}
