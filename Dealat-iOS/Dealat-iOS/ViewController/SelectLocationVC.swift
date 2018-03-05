//
//  SelectLocationVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/4/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SelectLocationVC: BaseVC,UITableViewDelegate,UITableViewDataSource {
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var okBtn: UIButton!
    
    var cities = [City]()
    var selectedCity : City!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.tableFooterView = UIView()
        self.okBtn.isHidden = true
        
        getData()
    }
    
    override func getRefreshing() {
        
        Communication.shared.get_countries { (res) in
            self.hideLoading()
            self.cities = res
            self.tableView.reloadData()
        }
//        Communication.shared.get_countries { (res) in
//            self.ci
//        }
//        Communication.shared.get_data_lists { (locations, _, _, _) in
//            self.hideLoading()
//            self.locations = locations
//            self.tableView.reloadData()
//        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.cities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        cell.selectionStyle = .none
        cell.textLabel?.text = self.cities[indexPath.row].city_name
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
            self.selectedCity = self.cities[indexPath.row]
            self.okBtn.isHidden = false
        }
    }
    
    @IBAction func okAction(){
        
        if let city = self.selectedCity {
            Provider.setCity(city.city_id.intValue)
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
            self.navigationController?.pushViewController(vc, animated: true)
        }
        
    }
    
    
    
}
