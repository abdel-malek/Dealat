//
//  CategoriesVC3.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class CategoriesVC3: BaseVC {

    @IBOutlet weak var tableView : UITableView!
    var cat : Cat = Cat()
    var chooseCatVC : ChooseCatVC!

    override func viewDidLoad() {
        super.viewDidLoad()

        self.title = cat.category_name
        
        tableView.delegate = self
        tableView.dataSource = self
    }


}


extension CategoriesVC3 : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cat.children.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! CategoryCell
        
        if indexPath.row == 0{
            if let c = Cat(JSON: self.cat.toJSON()){
                c.category_name = "All".localized
                cell.tag = -1
                cell.cat = c
            }
        }else{
            cell.cat = cat.children[indexPath.row - 1]
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == 0{
            
            if cat.category_id == nil{
            self.chooseCatVC.filterVC?.filter.category = nil
            self.chooseCatVC.filterVC?.refreshData()
                print("IFFFFF")
            }else{
                self.chooseCatVC.filterVC?.filter.category = cat
                self.chooseCatVC.filterVC?.refreshData()
                print("ELSEEEE")
            }
            self.chooseCatVC.dismiss(animated: true, completion: nil)
        }else{
            let c = cat.children[indexPath.row - 1]
            
            if !c.children.isEmpty{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "CategoriesVC3") as! CategoriesVC3
                vc.chooseCatVC = self.chooseCatVC
                vc.cat = c
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                self.chooseCatVC.filterVC?.filter.category = c
                self.chooseCatVC.filterVC?.refreshData()
                self.chooseCatVC.dismiss(animated: true, completion: nil)
            }
        }
        
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    
    
}
