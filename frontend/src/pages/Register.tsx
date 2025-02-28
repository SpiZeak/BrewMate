import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';

const Register = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  return (
    <Box
      component='form'
      onSubmit={e => e.preventDefault()}
      className='flex flex-col w-full max-w-sm mx-auto mt-8 p-4 shadow-md rounded space-y-4'
    >
      <Typography variant='h5'>Registration</Typography>
      <TextField
        type='email'
        label='Email'
        variant='outlined'
        required
        onChange={e => setEmail(e.target.value)}
        className='mb-4'
      />
      <TextField
        type='password'
        label='Password'
        variant='outlined'
        required
        onChange={e => setPassword(e.target.value)}
      />
      <Button type='submit' variant='contained' disabled={!email || !password}>
        Submit
      </Button>
    </Box>
  );
};

export default Register;
