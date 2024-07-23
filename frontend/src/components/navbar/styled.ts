import styled from '@emotion/styled'
import MuiContainer from '@mui/material/Container'
import { Link } from 'react-router-dom'

export const Nav = styled.nav`
  height: 60px;
`

export const Container = styled(MuiContainer)`
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`

export const Logo = styled(Link)`
  font-size: 32px;
  font-weight: 700;
`

export const List = styled.ul`
  display: flex;
  gap: 0 12px;
  list-style: none;
`

// TODO: 버튼 기본 CSS 속성 공통적으로 제거하기
export const LinkButton = styled(Link)`
  padding: 12px 10px;
  color: #425968;
  font-size: 14px;
  font-weight: 600;
  border: none;
  outline: none;
  border-radius: 8px;
  background-color: transparent;

  &:hover {
    cursor: pointer;
    background-color: rgb(2, 32, 71, 0.05);
  }
`
