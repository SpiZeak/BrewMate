import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';

const { VITE_BACKEND_URL } = import.meta.env;

const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const userData = {
      name,
      email,
      password,
    };
    try {
      const response = await fetch(`${VITE_BACKEND_URL}/users/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      console.log('User registered successfully:', data);
    } catch (error) {
      console.error('Error during registration:', error);
    }
  };

  return (
    <Box
      component='form'
      onSubmit={handleSubmit}
      className='flex flex-col w-full max-w-sm mx-auto mt-8 p-4 shadow-md rounded space-y-4'
    >
      <Typography variant='h5'>Registration</Typography>
      <TextField
        type='text'
        label='Name'
        variant='outlined'
        required
        onChange={e => setName(e.target.value)}
        className='mb-4'
        name='name'
      />
      <TextField
        type='email'
        label='Email'
        variant='outlined'
        required
        onChange={e => setEmail(e.target.value)}
        className='mb-4'
        name='email'
      />
      <TextField
        type='password'
        label='Password'
        variant='outlined'
        required
        onChange={e => setPassword(e.target.value)}
        name='password'
      />
      <Button type='submit' variant='contained' disabled={!email || !password}>
        Submit
      </Button>
    </Box>
  );
};

export default Register;
