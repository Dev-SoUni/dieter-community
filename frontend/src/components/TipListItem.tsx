import { useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import TableCell from '@mui/material/TableCell'
import IconButton from '@mui/material/IconButton'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import TableRow from '@mui/material/TableRow'

interface TipListItemProps {
  id: string
  title: string
  createdAt: string
}

export const TipListItem = ({ id, title, createdAt }: TipListItemProps) => {
  const navigate = useNavigate()
  const anchorRef = useRef<HTMLButtonElement>(null)
  const [open, setOpen] = useState<boolean>(false)
  const handleMoreClick = () => {
    setOpen(true)
  }

  const handleMoreClose = () => {
    setOpen(false)
  }

  return (
    <TableRow key={id}>
      <TableCell align="center">{id}</TableCell>
      <TableCell align="left">{title}</TableCell>
      <TableCell align="center">{createdAt}</TableCell>
      <TableCell align="center">
        <IconButton ref={anchorRef} onClick={handleMoreClick}>
          <MoreVertIcon />
        </IconButton>
        <Menu
          anchorEl={anchorRef.current}
          open={open}
          onClose={handleMoreClose}
        >
          <MenuItem onClick={() => navigate(`/tips/${id}`)}>
            자세히 보기
          </MenuItem>
        </Menu>
      </TableCell>
    </TableRow>
  )
}
