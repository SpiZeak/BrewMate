import { selectEmail } from '@features/user/userSlice';
import MuiAvatar, { AvatarOwnProps } from '@mui/material/Avatar';
import { useSelector } from 'react-redux';

const Avatar = (props: AvatarOwnProps) => {
  const username = useSelector(selectEmail) ?? '';

  function stringToColor(string: string) {
    let hash = 0;
    let i;

    for (i = 0; i < string.length; i += 1) {
      hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    // Generate base color
    let r = hash & 0xff;
    let g = (hash >> 8) & 0xff;
    let b = (hash >> 16) & 0xff;

    // Ensure color is not too dark by setting minimum RGB values
    const minBrightness = 100; // Adjust this value (0-255) to control brightness
    r = Math.max(r, minBrightness);
    g = Math.max(g, minBrightness);
    b = Math.max(b, minBrightness);

    // Convert to hex
    return `#${r.toString(16).padStart(2, '0')}${g
      .toString(16)
      .padStart(2, '0')}${b.toString(16).padStart(2, '0')}`;
  }

  const overrideProps = {
    sx: {
      bgcolor: stringToColor(username),
      ...props.sx,
    },
  };

  return <MuiAvatar {...overrideProps}>{username[0].toUpperCase()}</MuiAvatar>;
};

export default Avatar;
