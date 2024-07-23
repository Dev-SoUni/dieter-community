import Box from '@mui/material/Box'

import { useSession } from '../../hooks/useSession.tsx'

import * as Data from './data'
import * as SC from './styled'

export const Navbar = () => {
  const { session } = useSession()

  return (
    <SC.Nav>
      <SC.Container>
        {/* 왼쪽 */}
        <SC.Logo to="/">다커</SC.Logo>

        {/* 중앙 */}
        <SC.List>
          {Data.links.map((link) => (
            <li>
              <SC.LinkButton key={link.href} to={link.href}>
                {link.label}
              </SC.LinkButton>
            </li>
          ))}
        </SC.List>

        {/* 오른쪽 */}
        <Box>
          {session === 'unauthenticated' && (
            <SC.LinkButton to="/auth/login">로그인</SC.LinkButton>
          )}
          {session === 'authenticated' && (
            <SC.LinkButton to="/auth/logout">로그아웃</SC.LinkButton>
          )}
        </Box>
      </SC.Container>
    </SC.Nav>
  )
}
