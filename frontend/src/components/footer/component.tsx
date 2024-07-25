import { Link } from 'react-router-dom'
import Grid from '@mui/material/Grid'

import * as SC from './styled'
import * as Data from './data'

export const Footer = () => {
  return (
    <SC.Footer>
      <SC.Container>
        <SC.SiteGroupList container spacing={2}>
          {Data.sites.map((site) => (
            <Grid item sm={6} md={3} lg={2}>
              <SC.SiteGroup key={site.groupTitle}>
                <SC.SiteGroupTitle>{site.groupTitle}</SC.SiteGroupTitle>
                {site.groupList.map((group) => (
                  <SC.SiteGroupItem key={group.label}>
                    <SC.SiteGroupItemLink to={group.href}>
                      {group.label}
                    </SC.SiteGroupItemLink>
                  </SC.SiteGroupItem>
                ))}
              </SC.SiteGroup>
            </Grid>
          ))}
        </SC.SiteGroupList>
        <SC.SiteInfo>
          <SC.SiteName>다이어트 커뮤니티</SC.SiteName>
        </SC.SiteInfo>
        <SC.SocialList>
          {Data.socials.map((social) => (
            <SC.SocialItem key={social.label}>
              <Link to={social.href}>
                <SC.SocialImage src={social.img} alt={social.label} />
              </Link>
            </SC.SocialItem>
          ))}
        </SC.SocialList>
      </SC.Container>
    </SC.Footer>
  )
}
