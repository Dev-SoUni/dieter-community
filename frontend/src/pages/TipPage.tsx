import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'

import type { TipResponseDTO } from '../ts/dto.ts'

const TipPage = () => {
  const [tips, setTips] = useState<TipResponseDTO[]>([])

  useEffect(() => {
    const requestTips = async () => {
      const response = await fetch('http://localhost:8080/api/tips')
      const body = await response.json()
      setTips(body)
    }

    requestTips()
  }, [])

  return (
    <div>
      <h1>TIPS</h1>
      <div>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell align="center">아이디</TableCell>
                <TableCell align="left">제목</TableCell>
                <TableCell align="center">등록 날짜</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {tips.map((tip) => (
                <TableRow key={tip.id}>
                  <TableCell align="center">{tip.id}</TableCell>
                  <TableCell align="left">{tip.title}</TableCell>
                  <TableCell align="center">{tip.createdAt}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
      <Box mt={4} gap={2} display="flex" justifyContent="end">
        <Link to="/tips/new">
          <Button variant="contained">등록</Button>
        </Link>
        <Link to="/">
          <Button variant="contained">main</Button>
        </Link>
      </Box>
    </div>
  )
}

export default TipPage
