import React, { useState, useEffect, useContext } from "react";
import { FaHeart, FaRegHeart } from "react-icons/fa";

import AuthContext from "../../store/AuthContext";
import { axiosHttpInterceptor } from "../../config/interceptor/HttpInterceptor";

const Movie = ({ item, favoriteMovies }) => {
  const [like, setLike] = useState(false);

  useEffect(() => {
    favoriteMovies.map(fMovie => {
      if (item.id === fMovie.favoriteMovieIdentity.movieId) {
        setLike(true);
      }

      return null;
    })
  },[item,favoriteMovies]);

  const authCtx = useContext(AuthContext);

  const likeMovieParam = {
    "id": item.id,
    "title": item.title,
    "img": item.backdrop_path,
  };

  const addLike = () => {
    let url = `/mymovie/likes`;
    axiosHttpInterceptor
      .post(url, likeMovieParam)
      .then((response) => {
      })
      .catch((error) => {
        if (error.response) {
          if (error.response.data.status === 401) {
            alert("사용자 정보가 올바르지 않습니다.");
            authCtx.logout();
          }
        } else {
          console.log(error);
        }
      });
  };

  const saveLikeMovieHandler = () => {
    if (authCtx.isLoggedIn) {
      setLike(!like);

      addLike();
    } else {
      alert("로그인이 필요한 서비스 입니다.");
    }
  };

  return (
    <div className="w-[160px] sm:w-[200px] md:w-[240px] lg:w-[280px] inline-block cursor-pointer relative p-2">
      <img
        className="w-fll h-auto block"
        src={`https://image.tmdb.org/t/p/w500/${item?.backdrop_path}`}
        alt={item?.title}
      />
      <div className="absolute top-0 left-0 w-full h-full hover:bg-black/80 opacity-0 hover:opacity-100 text-white">
        <p className="whitespace-normal text-xs md:text-sm font-bold flex justify-center items-center h-full text-center">
          {item?.title}
        </p>
        <p onClick={saveLikeMovieHandler}>
          {like ? (
            <FaHeart className="absolute top-4 left-4 text-gray-300" />
          ) : (
            <FaRegHeart className="absolute top-4 left-4 text-gray-300" />
          )}
        </p>
      </div>
    </div>
  );
};

export default Movie;
