package backtesting.project

// class UrlMappings {
//     static mappings = {
//         "/$controller/$action?/$id?(.$format)?"{
//             constraints {
//                 // apply constraints here
//             }
//         }

//         "/"(view:"/index")
//         "500"(view:'/error')
//         "404"(view:'/notFound')

//     }
// }


class UrlMappings {
    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // Define specific URL constraints or patterns here if required
            }
        }

        "/"(view: "/index")
        "/backtest/run"(controller: "Backtest", action: "runBacktest")
        "/portfolio/update"(controller: "Portfolio", action: "updateHoldings")
        "/metrics/calculate"(controller: "Metrics", action: "calculate")

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
