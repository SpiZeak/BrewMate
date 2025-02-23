import { increment } from '@features/counter/counterSlice';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@app/store';
import Button from '@mui/material/Button';

function Home() {
  const count = useSelector((state: RootState) => state.counter.value);
  const dispatch = useDispatch();

  return (
    <>
      <h1>Home</h1>
      <Button variant='contained' onClick={() => dispatch(increment())}>
        count is {count}
      </Button>
    </>
  );
}

export default Home;
