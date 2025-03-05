import { useNavigate } from 'react-router';
import { useSelector } from 'react-redux';
import { selectIsAuthenticated } from '../features/user/userSlice';
import { ReactNode, useEffect } from 'react';

interface Props {
  redirectOnAuth?: boolean;
  redirectPath?: string;
  children: ReactNode;
}

const AuthRoute = ({
  children,
  redirectPath = '/',
  redirectOnAuth = false,
}: Props) => {
  const isAuthenticated = useSelector(selectIsAuthenticated);
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated && redirectOnAuth) {
      navigate(redirectPath);
    }

    if (!isAuthenticated && !redirectOnAuth) {
      navigate(redirectPath);
    }
  }, [isAuthenticated, redirectOnAuth, redirectPath, navigate]);

  // Render null during the effect execution cycle if we're going to redirect
  if (
    (isAuthenticated && redirectOnAuth) ||
    (!isAuthenticated && !redirectOnAuth)
  ) {
    return null;
  }

  // Otherwise render children
  return <>{children}</>;
};

export default AuthRoute;
