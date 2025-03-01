import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@app/store';
import Button from '@mui/material/Button';
import { Typography } from '@mui/material';

const Home = () => {
  const dispatch = useDispatch();

  return (
    <>
      <Typography variant='h4'>Home</Typography>
    </>
  );
};

export default Home;
