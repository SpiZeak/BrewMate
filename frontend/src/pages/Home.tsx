import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { useNavigate } from 'react-router';

const Home = () => {
  const navigate = useNavigate();

  return (
    <Box className='flex flex-col w-full max-w-sm mx-auto mt-8 p-4 shadow-md rounded space-y-4 MuiBox-root css-0'>
      <Typography variant='h4'>Welcome to BrewMate</Typography>
      <Typography variant='body1'>
        BrewMate is a simple app that helps you keep track of your homebrewing
        recipes. Log in to get started!
      </Typography>
      <Button
        variant='outlined'
        color='primary'
        onClick={() => {
          void navigate('/auth');
        }}
      >
        Log In
      </Button>
    </Box>
  );
};

export default Home;
