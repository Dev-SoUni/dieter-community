import { Link } from 'react-router-dom'

import * as SC from './styled'
import * as Data from './data'

export const Footer = () => {
  return (
    <SC.Footer>
      <SC.Container>
        <SC.SiteGroupList>
          {Data.sites.map((site) => (
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
