import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import LoginIcon from '@mui/icons-material/Login';
import { useState } from 'react';
import { Outlet, useNavigate } from 'react-router';
import HouseIcon from '@mui/icons-material/House';

const AppShell = () => {
  const navigate = useNavigate();
  const [value, setValue] = useState('/');

  const handleChange = async (_: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
    await navigate(newValue);
  };

  return (
    <>
      <Outlet />
      <BottomNavigation value={value} onChange={handleChange}>
        <BottomNavigationAction label='Home' value='/' icon={<HouseIcon />} />
        <BottomNavigationAction
          label='Login'
          value='/login'
          icon={<LoginIcon />}
        />
      </BottomNavigation>
    </>
  );
};

export default AppShell;
