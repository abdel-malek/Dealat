//
//  LocationsVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 2/20/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class LocationsVC: BaseVC,UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tableView : UITableView!
    
    var locations = [Location]()
    var query = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        
        tableView.tableFooterView = UIView()
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return locationTemp.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        let name = locationTemp[indexPath.row].city_name + " - " + locationTemp[indexPath.row].location_name
        cell.textLabel?.text = locationTemp[indexPath.row].location_name
        
        return cell
    }
    
    
    var locationTemp : [Location]{
        return locations.filter({$0.location_name.contains(query) || $0.city_name.contains(query)})
    }
    

}
