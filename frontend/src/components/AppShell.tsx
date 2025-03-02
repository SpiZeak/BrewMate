import { Outlet } from 'react-router';
import Box from '@mui/material/Box';
import BottomNavigation from './BottomNavigation';

const AppShell = () => {
  return (
    <Box>
      <Outlet />
      <BottomNavigation />
    </Box>
  );
};

export default AppShell;
