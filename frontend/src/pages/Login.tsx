import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { Link } from 'react-router';
import Typography from '@mui/material/Typography';
import { useDispatch } from 'react-redux';
import { setUser, UserState } from '@features/user/userSlice';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch();

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const response = await fetch('/login.json', {
      method: 'POST',
      body: formData,
    });

    const data: UserState = (await response.json()) as UserState;

    dispatch(setUser(data));
  }

  return (
    <Box
      component='form'
      onSubmit={handleSubmit}
      className='flex flex-col w-full max-w-sm mx-auto mt-8 p-4 shadow-md rounded space-y-4'
    >
      <TextField
        type='email'
        label='Email'
        variant='outlined'
        required
        onChange={e => {
          setEmail(e.target.value);
        }}
        className='mb-4'
        name='email'
      />
      <TextField
        type='password'
        label='Password'
        variant='outlined'
        required
        onChange={e => {
          setPassword(e.target.value);
        }}
        name='password'
      />
      <Button type='submit' variant='contained' disabled={!email || !password}>
        Submit
      </Button>
      <Typography>No account? Register here</Typography>
      <Link to='/auth/register'>
        <Button type='submit' variant='contained'>
          Register
        </Button>
      </Link>
    </Box>
  );
};

export default Login;
