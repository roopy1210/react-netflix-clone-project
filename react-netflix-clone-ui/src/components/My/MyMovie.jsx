import React, { useState, useEffect, useContext } from "react";
import { MdChevronLeft, MdChevronRight } from "react-icons/md";
import { AiOutlineClose } from "react-icons/ai";

import AuthContext from "../../store/AuthContext";
import { axiosHttpInterceptor } from "../../config/interceptor/HttpInterceptor";
import { useNavigate } from "react-router-dom";

const MyMovie = () => {
  const navigate = useNavigate();

  const [favoriteMovies, setFavoriteMovies] = useState([]);

  const authCtx = useContext(AuthContext);

  useEffect(() => {
    let url = `/mymovie/favorite/movies`;
    axiosHttpInterceptor
      .post(url)
      .then((response) => {
        setFavoriteMovies(response.data);
      })
      .catch((error) => {
        if (error.response) {
          console.log(error.response);
        } else {
        }
      });
  }, [favoriteMovies]);

  const slideLeft = () => {
    var slider = document.getElementById("slider");
    slider.scrollLeft = slider.scrollLeft - 500;
  };

  const slideRight = () => {
    var slider = document.getElementById("slider");
    slider.scrollLeft = slider.scrollLeft + 500;
  };

  const deleteMovie = (movieId) => {
    if (!authCtx.isLoggedIn) {
      alert("로그인이 필요한 서비스 입니다.");
      navigate("/");
    } else {
      let url = `/mymovie/favorite/movie/${movieId}`;
      axiosHttpInterceptor
        .post(url)
        .then((response) => {
          if (response.status === 200) {
            setFavoriteMovies(response.data);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  return (
    <>
      <h2 className="text-white font-bold md:text-xl p-4">My Movies</h2>
      <div className="relative flex items-center group">
        <MdChevronLeft
          onClick={slideLeft}
          className="bg-white left-0 rounded-full absolute opacity-50 hover:opacity-100 cursor-pointer z-10 hidden group-hover:block"
          size={40}
        />
        <div
          id={"slider"}
          className="w-full h-full overflow-x-scroll whitespace-nowrap scroll-smooth scrollbar-hide relative"
        >
          {favoriteMovies.map((item) => (
            <div
              key={item.favoriteMovieIdentity.movieId}
              className="w-[160px] sm:w-[200px] md:w-[240px] lg:w-[280px] inline-block cursor-pointer relative p-2"
            >
              <img
                className="w-full h-auto block"
                src={`http://image.tmdb.org/t/p/w500/${item?.img}`}
                alt={item?.title}
              />
              <div className="absolute top-0 left-0 w-full h-full hover:bg-black/80 opacity-0 hover:opacity-100 text-white">
                <p className="white-space-normal text-xs md:text-sm font-bold flex justify-center items-center h-full text-center">
                  {item?.title}
                </p>
                <p
                  onClick={() =>
                    deleteMovie(item.favoriteMovieIdentity.movieId)
                  }
                  className="absolute text-gray-300 top-4 right-4"
                >
                  <AiOutlineClose />
                </p>
              </div>
            </div>
          ))}
        </div>
        <MdChevronRight
          onClick={slideRight}
          className="bg-white right-0 rounded-full absolute opacity-50 hover:opacity-100 cursor-pointer z-10 hidden group-hover:block"
          size={40}
        />
      </div>
    </>
  );
};

export default MyMovie;
