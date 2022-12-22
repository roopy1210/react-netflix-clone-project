import React, { useState, useEffect, useContext } from "react";
import Main from "../components/Main";
import Row from "../components/Layout/Row";
import requests from "../Requests";

import AuthContext from "../store/AuthContext";
import { axiosHttpInterceptor } from "../config/interceptor/HttpInterceptor";

const Home = () => {
  const authCtx = useContext(AuthContext);

  const [favoriteMovies, setFavoriteMovies] = useState([]);

  useEffect(() => {
    if (authCtx.isLoggedIn) {
      let url = `/mymovie/favorite/movies`;
      axiosHttpInterceptor
        .post(url)
        .then((response) => {
          setFavoriteMovies(response.data);
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    }
  }, [authCtx]);

  return (
    <>
      <Main />
      <Row
        rowID="1"
        title="Up Coming"
        fetchURL={requests.requestUpcoming}
        favoriteMovies={favoriteMovies}
      />
      <Row
        rowID="2"
        title="Popular"
        fetchURL={requests.requestPopular}
        favoriteMovies={favoriteMovies}
      />
      <Row
        rowID="3"
        title="Trending"
        fetchURL={requests.requestTrending}
        favoriteMovies={favoriteMovies}
      />
      <Row
        rowID="4"
        title="Top Rated"
        fetchURL={requests.requestTopRated}
        favoriteMovies={favoriteMovies}
      />
      <Row
        rowID="5"
        title="Horror"
        fetchURL={requests.requestHorror}
        favoriteMovies={favoriteMovies}
      />
    </>
  );
};

export default Home;
