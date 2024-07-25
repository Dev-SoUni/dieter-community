import styled from '@emotion/styled'
import MuiGrid from '@mui/material/Grid'
import MuiContainer from '@mui/material/Container'
import { Link } from 'react-router-dom'

export const Footer = styled.footer`
  background-color: #191f28;
  padding: 50px 67px 100px 67px;
`

export const Container = styled(MuiContainer)`
  height: 100%;
`

export const SiteGroupList = styled(MuiGrid)`
  display: flex;
  padding-bottom: 50px;
`

export const SiteGroup = styled.ul`
  width: 170px;
  list-style: none;
  line-height: 30px;
`

export const SiteGroupTitle = styled.li`
  color: #b0b8c1;
  list-style: none;
  padding-bottom: 5px;
`

export const SiteGroupItem = styled.li`
  color: #6b7684;
`

export const SiteGroupItemLink = styled(Link)`
  color: #425968;
  font-size: 14px;
`

export const SiteInfo = styled.div`
  padding-bottom: 50px;
`

export const SiteName = styled.strong`
  font-size: 15px;
  color: #8b95a1;
`

export const SocialList = styled.ul`
  display: flex;
  list-style: none;
`

export const SocialItem = styled.li`
  margin-right: 8px;
`

export const SocialImage = styled.img`
  width: 32px;
  height: 32px;
`
