//
//  SelectLocationVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/4/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SelectLocationVC: BaseVC,UITableViewDelegate,UITableViewDataSource {
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var okBtn: UIButton!
    
    var locations = [Location]()
    var selectedLocation : Location!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.tableFooterView = UIView()
        self.okBtn.isHidden = true
        
        getData()
    }
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, _, _, _) in
            self.hideLoading()
            self.locations = locations
            self.tableView.reloadData()
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.locations.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        cell.selectionStyle = .none
        cell.textLabel?.text = self.locations[indexPath.row].location_name
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        for i in tableView.visibleCells{
            i.accessoryType = .none
        }
        if let cell = tableView.cellForRow(at: indexPath){
            cell.accessoryType = .none
            cell.accessoryType = .checkmark
            cell.tintColor = .white
            self.selectedLocation = self.locations[indexPath.row]
            self.okBtn.isHidden = false
        }
    }
    
    @IBAction func okAction(){
        
        if let location = self.selectedLocation {
            Provider.setLocation(location.location_id.intValue)
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
            self.navigationController?.pushViewController(vc, animated: true)
        }
        
    }
    
    
    
}
