import { BrowserRouter as Router, Routes, Route } from 'react-router';
import { Suspense, lazy } from 'react';
import { ReactNode } from 'react';
import Register from '@pages/Register';
import AuthRoute from '../components/AuthRoute';

const AppShell = lazy(() => import('../components/AppShell'));
const Home = lazy(() => import('../pages/Home'));
const Login = lazy(() => import('../pages/Login'));
const Profile = lazy(() => import('../pages/Profile'));
const ErrorBoundary = ({ children }: { children: ReactNode }) => {
  try {
    return children;
  } catch {
    return <div>Error loading component</div>;
  }
};

const AppRouter = () => (
  <Router>
    <ErrorBoundary>
      <Suspense fallback={<div>Loading...</div>}>
        <Routes>
          <Route path='/' element={<AppShell />}>
            <Route index element={<Home />} />
            <Route path='auth'>
              <Route
                index
                element={
                  <AuthRoute redirectOnAuth>
                    <Login />
                  </AuthRoute>
                }
              />
              <Route
                path='register'
                element={
                  <AuthRoute redirectOnAuth>
                    <Register />
                  </AuthRoute>
                }
              />
            </Route>
            <Route
              path='profile'
              element={
                <AuthRoute redirectPath='/auth'>
                  <Profile />
                </AuthRoute>
              }
            />
          </Route>
        </Routes>
      </Suspense>
    </ErrorBoundary>
  </Router>
);

export default AppRouter;
