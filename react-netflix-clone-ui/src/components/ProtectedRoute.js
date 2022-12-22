import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import AuthContext from "../store/AuthContext";

const ProtectedRoute = ({ children }) => {
    const authCtx = useContext(AuthContext);

    if (!authCtx.isLoggedIn) {
        return <Navigate to='/' />;
    }
    else {
        return children;
    }
}

export default ProtectedRoute;