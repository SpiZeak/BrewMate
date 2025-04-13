import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { useNavigate } from 'react-router';

const Home = () => {
  const navigate = useNavigate();

  return (
    <Box>
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
