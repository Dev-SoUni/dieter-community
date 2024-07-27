import { useState } from 'react'
import Button from '@mui/material/Button'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import DialogContentText from '@mui/material/DialogContentText'
import DialogTitle from '@mui/material/DialogTitle'
import WarningIcon from '@mui/icons-material/Warning'

interface DeleteButtonProps {
  onConfirm: () => void
}
export const DeleteButton = ({ onConfirm }: DeleteButtonProps) => {
  const [open, setOpen] = useState<boolean>(false)

  const handleOpen = () => setOpen(true)

  const handleClose = () => setOpen(false)

  const handleConfirm = () => {
    onConfirm()
    handleClose()
  }

  return (
    <>
      <Button variant="outlined" color="warning" onClick={handleOpen}>
        삭제
      </Button>
      <Dialog
        open={open}
        maxWidth="xs"
        fullWidth
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle
          id="alert-dialog-title"
          sx={{
            display: 'flex',
            alignItems: 'center',
            gap: 1,
          }}
        >
          <WarningIcon color="warning" />
          정말 삭제하시겠습니까?
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            데이터가 삭제된 후에는 복구할 수 없습니다.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button variant="outlined" color="secondary" onClick={handleClose}>
            취소
          </Button>
          <Button variant="contained" autoFocus onClick={handleConfirm}>
            삭제
          </Button>
        </DialogActions>
      </Dialog>
    </>
  )
}
