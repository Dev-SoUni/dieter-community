import { useEffect, useRef } from 'react'
import { Link } from 'react-router-dom'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Typography from '@mui/material/Typography'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Pagination from '@mui/material/Pagination'

import { useAppSelector, useAppDispatch } from '../app/hook.ts'
import {
  setPageableData,
  setPage,
  setSearch,
} from '../features/tip/tipSlice.ts'
import { defaultAxios } from '../config/axios.ts'
import Search from '../components/search'
import { TipListItem } from '../components/TipListItem.tsx'
import CustomHelmet from '../components/helmet'

const TipPage = () => {
  const dispatch = useAppDispatch()
  const { pageableData, page, search } = useAppSelector((state) => state.tip)

  const searchTitleRef = useRef<HTMLInputElement>(null)

  useEffect(() => {
    const requestTips = async () => {
      try {
        const response = await defaultAxios.get(`/api/tips`, {
          params: {
            page: page,
            title: search.title,
          },
        })
        dispatch(setPageableData(response.data))
      } catch (error) {
        alert('관리자에게 문의주세요.')
      }
    }
    requestTips()
  }, [page, search])

  const handleSearchSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const value = searchTitleRef.current?.value || ''
    dispatch(setPage(0))
    dispatch(setSearch({ title: value }))
  }

  const handlePaginationChange = (
    _: React.ChangeEvent<unknown>,
    value: number,
  ) => {
    dispatch(setPage(value - 1))
  }

  return (
    <div>
      <h1>TIPS</h1>
      <CustomHelmet title="꿀팁 | 다커" description="꿀팁 페이지입니다." />
      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={2}
      >
        {/* 검색 정보*/}
        <Box>
          {search.title && (
            <Typography>'{search.title}' 에 대한 검색 결과입니다.</Typography>
          )}
        </Box>
        {/* 검색 */}
        <form onSubmit={handleSearchSubmit}>
          <Search inputRef={searchTitleRef} defaultValue={search.title} />
        </form>
      </Box>

      <div>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell align="center">아이디</TableCell>
                <TableCell align="left">제목</TableCell>
                <TableCell align="center">등록 날짜</TableCell>
                <TableCell align="center" sx={{ width: 60 }} />
              </TableRow>
            </TableHead>
            <TableBody>
              {pageableData &&
                pageableData.content.map((tip) => (
                  <TipListItem
                    key={tip.id}
                    id={tip.id}
                    title={tip.title}
                    createdAt={tip.createdAt}
                  />
                ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
      <Box mt={4} display="flex" justifyContent="center">
        {pageableData && (
          <Pagination
            count={pageableData.totalPages}
            page={page + 1}
            color="primary"
            onChange={handlePaginationChange}
          />
        )}
      </Box>
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
