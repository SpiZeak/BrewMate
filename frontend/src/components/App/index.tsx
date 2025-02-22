import reactLogo from '@assets/images/react.svg';
import viteLogo from '/vite.svg';
import styles from './App.module.scss';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@app/store';
import { increment } from '@features/counter/counterSlice';

function App() {
  const count = useSelector((state: RootState) => state.counter.value);
  const dispatch = useDispatch();

  return (
    <>
      <div>
        <a href='https://vite.dev' target='_blank' rel='noreferrer'>
          <img src={viteLogo} className={styles.logo} alt='Vite logo' />
        </a>
        <a href='https://react.dev' target='_blank' rel='noreferrer'>
          <img
            src={reactLogo}
            className={`${styles.logo} ${styles.react}`}
            alt='React logo'
          />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className={styles.card}>
        <button onClick={() => dispatch(increment())}>count is {count}</button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className={styles.readTheDocs}>
        Click on the Vite and React logos to learn more
      </p>
    </>
  );
}

export default App;
