import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { API_URL } from '@app/constants';

const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    setLoading(true);

    const userData = {
      name,
      email,
      password,
    };
    try {
      const response = await fetch(`${API_URL}/users/auth/register`, {
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

      const data = await response.json();
      console.log('User registered successfully:', data);
    } catch (error) {
      setLoading(false);
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
      <Button
        type='submit'
        variant='contained'
        disabled={!email || !password}
        loading={loading}
      >
        Submit
      </Button>
    </Box>
  );
};

export default Register;
