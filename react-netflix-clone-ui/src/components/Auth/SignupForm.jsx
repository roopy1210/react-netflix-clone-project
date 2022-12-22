import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";

import { MdErrorOutline } from "react-icons/md";

const SignupForm = () => {
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const emailChangeHandler = (event) => {
    setEmail(event.target.value);
  };

  const passwordChangeHandler = (event) => {
    setPassword(event.target.value);
  };

  const submitHandler = (event) => {
    event.preventDefault();

    const enteredEmail = email;
    const enteredPassword = password;

    // Signup API 호출
    let url = "http://localhost:9090/signup";
    axios
      .post(url, {
        username: enteredEmail,
        password: enteredPassword,
      })
      .then((response) => {
        if (response.status === 201) {
          setError(true);
          setErrorMessage(response.data.errorMessage);

          setEmail('');
          setPassword('');
        }
      })
      .catch((error) => {
        if (error.response) {
          setError(true);
          setErrorMessage(error.response.data.errorMessage);

          setEmail('');
          setPassword('');
        }
      });
  };

  return (
    <>
      <div className="w-full h-screen">
        <img
          className="hidden sm:block absolute w-full h-full object-cover"
          src="https://assets.nflxext.com/ffe/siteui/vlv3/f841d4c7-10e1-40af-bcae-07a3f8dc141a/f6d7434e-d6de-4185-a6d4-c77a2d08737b/US-en-20220502-popsignuptwoweeks-perspective_alpha_website_medium.jpg"
          alt="/"
        />
        <div className="bg-black/60 fixed top-0 left-0 w-full h-screen"></div>
        <div className="fixed w-full px-4 py-24 z-50">
          <div className="max-w-[450px] h-[600px] mx-auto bg-black/75 text-white">
            <div className="max-w-[320px] mx-auto py-16">
              <h1 className="text-3xl font-bold">Sign Up</h1>
              <form
                className="w-full flex flex-col py-4"
                onSubmit={submitHandler}
              >
                <input
                  className="p-3 my-2 bg-gray-700 rounded"
                  type="email"
                  placeholder="Email"
                  autoComplete="email"
                  required
                  onChange={emailChangeHandler}
                  value={email}
                />
                <input
                  className="p-3 my-2 bg-gray-700 rounded"
                  type="password"
                  placeholder="Password"
                  autoComplete="current-password"
                  required
                  onChange={passwordChangeHandler}
                  value={password}
                />
                <button className="bg-red-600 py-3 my-6 rounded font-bold">
                  Sign Up
                </button>
                {error && (
                  <div className="flex items-center">
                    <MdErrorOutline className="fill-yellow-100" />
                    <span className="ml-1 text-xs text-yellow-100">
                      {errorMessage}
                    </span>
                  </div>
                )}
                <p className="py-8">
                  <span className="text-gray-600">
                    Already subscribed to Netflix?
                  </span>{" "}
                  <Link to="/signin">Sign In</Link>
                </p>
              </form>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default SignupForm;
