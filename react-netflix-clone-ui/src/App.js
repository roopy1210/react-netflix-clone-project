import { Route, Routes } from "react-router-dom";
import Navbar from "./components/Layout/Navbar";
import Home from "./pages/Home";
import Signup from "./pages/Signup";
import Signin from "./pages/Signin";
import MyMovie from "./pages/MyMovie";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signin" element={<Signin />} />
        <Route path="/signup" element={<Signup />} />
        <Route
          path="/mymovie"
          element={
            <ProtectedRoute>
              <MyMovie />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default App;
