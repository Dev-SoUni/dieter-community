import { Helmet } from 'react-helmet-async'

interface HelmetProps {
  title: string
  description?: string
}

export const CustomHelmet = ({ title, description }: HelmetProps) => {
  return (
    <Helmet>
      <title>{title}</title>
      {description && <meta name="description" content={description} />}
    </Helmet>
  )
}
