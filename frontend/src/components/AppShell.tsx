import { Outlet } from 'react-router';
import Box from '@mui/material/Box';
import BottomNavigation from './BottomNavigation';
import { styled } from '@mui/material/styles';

const StyledBox = styled(Box)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  width: '100%',
  maxWidth: '24rem', // equivalent to max-w-sm
  margin: '2rem auto 0', // equivalent to mx-auto mt-8
  padding: theme.spacing(2), // equivalent to p-4
  boxShadow: theme.shadows[3], // equivalent to shadow-md
  borderRadius: theme.shape.borderRadius,
  '& > *:not(:last-child)': {
    marginBottom: theme.spacing(2), // equivalent to space-y-4
  },
}));

const AppShell = () => {
  return (
    <StyledBox>
      <Outlet />
      <BottomNavigation />
    </StyledBox>
  );
};

export default AppShell;
