import { increment } from '@features/counter/counterSlice';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@app/store';
import Button from '@mui/material/Button';
import { Typography } from '@mui/material';

const Home = () => {
  const count = useSelector((state: RootState) => state.counter.value);
  const dispatch = useDispatch();

  return (
    <>
      <Typography variant='h4'>Home</Typography>
      <Button variant='contained' onClick={() => dispatch(increment())}>
        count is {count}
      </Button>
    </>
  );
};

export default Home;
