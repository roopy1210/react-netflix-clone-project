import React from "react";
import { Link } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../../store/AuthContext";

const Navbar = () => {
  const authCtx = useContext(AuthContext);

  const logoutHandler = () => {
    authCtx.logout();
  };

  return (
    <div className="flex items-center justify-between p-4 z-[100] w-full absolute">
      <Link to="/">
        <h1 className="text-red-600 text-4xl font-bold cursor-pointer">
          NETFLIX
        </h1>
      </Link>
      {authCtx.isLoggedIn ? (
        <div>
          <Link to="/mymovie">
            <button className="text-white pr-4">My Movie</button>
          </Link>
          <button
            className="bg-red-600 px-6 py-2 rounded cursor-pointer text-white"
            onClick={logoutHandler}
          >
            Logout
          </button>
        </div>
      ) : (
        <div>
          <Link to="/signin">
            <button className="text-white pr-4">Sign In</button>
          </Link>
          <Link to="/signup">
            <button className="bg-red-600 px-6 py-2 rounded cursor-pointer text-white">
              Sign Up
            </button>
          </Link>
        </div>
      )}
    </div>
  );
};

export default Navbar;
