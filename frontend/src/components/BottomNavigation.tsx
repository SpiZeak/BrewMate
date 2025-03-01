import { useEffect, useState } from 'react';
import MuiBottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import LoginIcon from '@mui/icons-material/Login';
import HouseIcon from '@mui/icons-material/House';
import Paper from '@mui/material/Paper';
import { useLocation, useNavigate } from 'react-router';
import Avatar from './Avatar';
import { useSelector } from 'react-redux';
import {
  selectIsAuthenticated,
  selectUsername,
} from '@features/user/userSlice';

const BottomNavigation = () => {
  const username = useSelector(selectUsername);
  const navigate = useNavigate();
  const location = useLocation();
  const [value, setValue] = useState(location.pathname);
  const isAuthenticated = useSelector(selectIsAuthenticated);

  useEffect(() => {
    setValue(location.pathname);
  }, [location.pathname]);

  const handleChange = async (_: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
    await navigate(newValue);
  };

  return (
    <Paper className='fixed bottom-0 left-0 right-0' elevation={3}>
      <MuiBottomNavigation value={value} onChange={handleChange}>
        <BottomNavigationAction label='Home' value='/' icon={<HouseIcon />} />
        {!isAuthenticated && (
          <BottomNavigationAction
            label='Login'
            value='/auth'
            icon={<LoginIcon />}
          />
        )}
        {isAuthenticated && (
          <BottomNavigationAction
            label={username}
            value='/profile'
            icon={<Avatar sx={{ width: 24, height: 24 }} />}
          />
        )}
      </MuiBottomNavigation>
    </Paper>
  );
};

export default BottomNavigation;
