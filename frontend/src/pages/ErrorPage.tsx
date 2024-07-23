import { useRouteError } from 'react-router-dom'

import ErrorMessage from '../components/error/error-message'

const ErrorPage = () => {
  const error = useRouteError()

  console.log(error)

  return <ErrorMessage />
}

export default ErrorPage
