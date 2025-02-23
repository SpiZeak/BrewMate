import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@app/store';
import { increment } from '@features/counter/counterSlice';
import Button from '@mui/material/Button';

function App() {
  const count = useSelector((state: RootState) => state.counter.value);
  const dispatch = useDispatch();

  return (
    <>
      <h1 className='text-5xl font-extrabold text-center text-blue-600 mt-8'>
        Hello world!
      </h1>
      <div>
        <Button variant='contained' onClick={() => dispatch(increment())}>
          count is {count}
        </Button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p>Click on the Vite and React logos to learn more</p>
    </>
  );
}

export default App;
