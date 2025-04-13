import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { Link } from 'react-router';
import Typography from '@mui/material/Typography';
import { useDispatch } from 'react-redux';
import { setUser } from '@features/user/userSlice';
import { API_URL } from '@app/constants';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    setLoading(true);

    const userData = {
      email,
      password,
    };
    try {
      const response = await fetch(`${API_URL}/users/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      dispatch(setUser({ email }));
    } catch (error) {
      console.error('Error during login:', error);
      setLoading(false);
    }
  }

  return (
    <Box
      component='form'
      onSubmit={e => {
        void handleSubmit(e);
      }}
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
      <Button
        type='submit'
        variant='contained'
        disabled={!email || !password}
        loading={loading}
      >
        Submit
      </Button>
      <Typography>No account?</Typography>
      <Link to='/auth/register'>
        <Button type='submit' variant='contained'>
          Register
        </Button>
      </Link>
    </Box>
  );
};

export default Login;
