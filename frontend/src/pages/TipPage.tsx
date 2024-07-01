import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

const TipPage = () => {
  const [tips, setTips] = useState<any[]>([])

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
        <ul>
          {tips.map((tip) => (
            <li key={tip.id}>{tip.title}</li>
          ))}
        </ul>
      </div>
      <Link to="/tips/new">등록</Link>
      <Link to="/">main</Link>
    </div>
  )
}

export default TipPage
