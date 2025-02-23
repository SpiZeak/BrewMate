import FormControl from '@mui/material/FormControl';
import Input from '@mui/material/Input';
import InputLabel from '@mui/material/InputLabel';
import FormHelperText from '@mui/material/FormHelperText';

const Login = () => {
  return (
    <FormControl>
      <InputLabel htmlFor='my-input'>Email address</InputLabel>
      <Input id='my-input' aria-describedby='my-helper-text' />
      <FormHelperText id='my-helper-text'>
        We'll never share your email.
      </FormHelperText>
    </FormControl>
  );
};

export default Login;
